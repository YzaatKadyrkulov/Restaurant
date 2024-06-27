package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "base_id_gen", sequenceName = "menuitem_seq", allocationSize = 1)
public class Menuitem extends BaseEntity{
    private String name;
    private String image;
    @NotNull
    @Min(value = 0, message = "Price cannot be negative")
    private double price;
    private String description;
    private boolean isVegetarian;

    @ManyToMany(mappedBy = "menuItems",cascade = {MERGE,REFRESH,REMOVE})
    private List<Cheque>cheques = new ArrayList<>();

    @ManyToOne(cascade = {MERGE,REFRESH,DETACH})
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.ALL,orphanRemoval = true)
    private StopList stopList;

    @ManyToOne(cascade = {MERGE,REFRESH,REMOVE})
    private SubCategory subCategory;
}
