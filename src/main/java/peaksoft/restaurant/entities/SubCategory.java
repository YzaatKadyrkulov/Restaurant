package peaksoft.restaurant.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen", sequenceName = "subcategory_seq", allocationSize = 1)
public class SubCategory extends BaseEntity {
    private String name;

    @ManyToOne(cascade = {REFRESH,DETACH})
    private Menuitem menuitem;

    @ManyToOne(cascade = {REFRESH,DETACH})
    private Category category;
}
