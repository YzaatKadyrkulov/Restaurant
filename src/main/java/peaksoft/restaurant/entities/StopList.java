package peaksoft.restaurant.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "stop_lists")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen", sequenceName = "stop_list_seq",allocationSize = 1)
public class StopList extends BaseEntity{
    private String reason;
    private LocalDate date;

    @OneToOne(cascade = {MERGE,REFRESH,DETACH}, orphanRemoval = true)
    private Menuitem menuitem;

    @Override
    public int hashCode() {
        return Objects.hash(menuitem, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StopList stopList = (StopList) obj;
        return Objects.equals(menuitem, stopList.menuitem) &&
                Objects.equals(date, stopList.date);
    }
//    Для того чтобы сохранить в StopList только одну запись для определенной еды на определенную дату, вам следует сделать следующее:
//
//            1. Изменение сущности StopList
//    Вам нужно изменить сущность StopList таким образом, чтобы она уникально идентифицировалась по комбинации menuitem и date.
}
