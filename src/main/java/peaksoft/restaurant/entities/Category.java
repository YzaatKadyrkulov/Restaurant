package peaksoft.restaurant.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen", sequenceName = "category_seq", allocationSize = 1)
public class Category extends BaseEntity{
    private String name;

    @OneToMany(mappedBy = "category",cascade = {MERGE,REFRESH,REMOVE},orphanRemoval = true)
    private List<SubCategory>subcategories;
}
