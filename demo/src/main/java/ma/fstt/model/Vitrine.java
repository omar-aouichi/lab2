package ma.fstt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vitrine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomTheme;

    @OneToMany(mappedBy = "vitrine")
    private List<Produit> produits;
}
