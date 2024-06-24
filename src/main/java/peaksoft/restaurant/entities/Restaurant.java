package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.restaurant.enums.RestType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen", sequenceName = "restaurant_seq",allocationSize = 1)
public class Restaurant extends BaseEntity {
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private RestType restType;
    @Max(value = 15, message = "No vacancies")
    private Long numberOfEmployees;
    private int service;

    @OneToMany(mappedBy = "restaurant",cascade = {MERGE,REFRESH,REMOVE}, orphanRemoval = true)
    private List<User> users;

    @OneToMany(mappedBy = "restaurant",cascade = {MERGE,REFRESH,REMOVE}, orphanRemoval = true)
    private List<Menuitem>menuItems;
}
