package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen", sequenceName = "subcategory_seq", allocationSize = 1)
public class SubCategory extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "subCategory", cascade = {MERGE, REFRESH, DETACH,REMOVE}, orphanRemoval = true)
    private List<Menuitem> menuItems = new ArrayList<>();

    @ManyToOne(cascade = {MERGE, REFRESH,DETACH,REMOVE})
    private Category category;
}
