package co.edu.uniquindio.arbol;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Imports para generar JSON manualmente
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class TreeController {

    private BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    private String lastMessage = "Bienvenido al gestor de Árboles.";
    // Variable para controlar el tipo de alerta de Bootstrap (success, danger, warning)
    private String alertType = "primary"; 

    @GetMapping("/")
    public String index(Model model) {
        // Enviar datos normales
        model.addAttribute("isEmpty", tree.isEmpty());
        model.addAttribute("inOrder", tree.getInOrder());
        model.addAttribute("preOrder", tree.getPreOrder());
        model.addAttribute("postOrder", tree.getPostOrder());
        model.addAttribute("size", tree.getSize());
        model.addAttribute("height", tree.getHeight());
        model.addAttribute("leaves", tree.countLeaves());
        model.addAttribute("min", tree.getMinorNode());
        model.addAttribute("max", tree.getMajorNode());
        
        // Enviar mensajes y su tipo
        model.addAttribute("message", lastMessage);
        model.addAttribute("alertType", alertType);
        
        // --- NUEVO: Generar estructura para Graficar en JS ---
        String treeJson = "[]"; // Por defecto vacío
        if (!tree.isEmpty()) {
            treeJson = generateGraphData(tree);
        }
        model.addAttribute("treeJsonData", treeJson);
        // ---------------------------------------------------

        return "index";
    }

    @PostMapping("/insert")
    public String insert(@RequestParam("val") Integer val) {
        // REQUERIMIENTO: Verificar si el valor ya existe
        if (tree.exists(val)) {
            lastMessage = "❌ Error: El valor " + val + " ya se encuentra en el árbol. No se permiten duplicados.";
            alertType = "danger"; // Rojo (Error)
        } else {
            tree.insert(val);
            lastMessage = "✅ Dato " + val + " insertado correctamente.";
            alertType = "success"; // Verde (Éxito)
        }
        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(@RequestParam("val") Integer val) {
        if (tree.exists(val)) {
            int level = tree.getLevel(val);
            lastMessage = "🔍 ¡Encontrado! " + val + " está en el nivel: " + level + ".";
            alertType = "info"; 
        } else {
            lastMessage = "⚠️ El dato " + val + " no se encuentra.";
            alertType = "warning"; // Amarillo (Advertencia)
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("val") Integer val) {
        if (tree.exists(val)) {
            tree.delete(val);
            lastMessage = "🗑️ Dato " + val + " eliminado.";
            alertType = "success";
        } else {
            lastMessage = "⚠️ Intento de eliminación fallido: " + val + " no existe.";
            alertType = "warning";
        }
        return "redirect:/";
    }

    @GetMapping("/clear")
    public String clear() {
        tree.clearTree();
        lastMessage = "💥 Árbol borrado por completo.";
        alertType = "dark";
        return "redirect:/";
    }

    // --- Funciones auxiliares para Graficado (JSON) ---

    // GoJS necesita una estructura: [{key: 'Riz', text: 'Raiz'}, {key: '5', text: '5', parent: 'Raiz'}]
    private String generateGraphData(BinarySearchTree<Integer> bst) {
        List<Map<String, Object>> nodeList = new ArrayList<>();
        populateJsonModel(bst.root, nodeList, null); // Empezamos sin padre para la raíz

        // Convertir la lista de Mapas a un String JSON real
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(nodeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    // Recorrido recursivo para armar el JSON
    private void populateJsonModel(Node<Integer> node, List<Map<String, Object>> list, String parentKey) {
        if (node == null) return;

        Map<String, Object> nodeMap = new HashMap<>();
        String currentKey = node.getData().toString(); // Usamos el valor como Key única (ya que evitamos duplicados)
        
        nodeMap.put("key", currentKey);
        nodeMap.put("text", currentKey);
        
        if (parentKey != null) {
            nodeMap.put("parent", parentKey);
        }
        
        list.add(nodeMap);
        
        // Recursión
        populateJsonModel(node.getLeft(), list, currentKey);
        populateJsonModel(node.getRight(), list, currentKey);
    }
}