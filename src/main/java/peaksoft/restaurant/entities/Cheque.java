package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Setter
@Entity
@Table(name = "cheques")
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen", sequenceName = "cheque_seq",allocationSize = 1)
public class Cheque extends BaseEntity {
    @Getter
    private int priceAverage;

    @Getter
    private LocalDate localDate;

    @OneToMany(mappedBy = "cheque",cascade = {MERGE,REFRESH,DETACH,REMOVE})
    private List<User> users = new ArrayList<>();

    @Getter
    @ManyToMany(mappedBy = "cheques",cascade = {MERGE,REFRESH,DETACH,REMOVE})
    private List<Menuitem>menuItems;

    @ElementCollection
    private List<String> items;

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

}
