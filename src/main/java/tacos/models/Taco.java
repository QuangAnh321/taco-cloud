package tacos.models;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class Taco {

    private Long id;
    
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min=1, message="You must choose at least 1 ingredient")
    private ArrayList<Ingredient> ingredients;

    private Date createAt = new Date();
    
}
