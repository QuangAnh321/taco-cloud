package tacos.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import tacos.data.IngredientRepository;
import tacos.models.*;
import tacos.models.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        Type[] types = Ingredient.Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(),
             filterByType(ingredients, type));
        }
    }

    private Iterable<Ingredient> filterByType(
        Iterable<Ingredient> ingredients, Type type
    ) {
        List<Ingredient> ingredientsAsList = StreamSupport
            .stream(ingredients.spliterator(), false)
            .collect(Collectors.toList());

        return ingredientsAsList
            .stream()
            .filter(ingredient -> ingredient.getType().equals(type))
            .collect(Collectors.toList());
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("taco", new Taco());
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid @ModelAttribute("taco") Taco taco, Errors errors) {
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing taco: "+taco);
        return "redirect:/orders/current";
    }
}
