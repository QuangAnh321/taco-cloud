package tacos;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Taco {
    
    private String name;

    private ArrayList<Ingredient> ingredients;
}
