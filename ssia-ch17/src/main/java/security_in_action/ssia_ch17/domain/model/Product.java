package security_in_action.ssia_ch17.domain.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private String owner;
}
