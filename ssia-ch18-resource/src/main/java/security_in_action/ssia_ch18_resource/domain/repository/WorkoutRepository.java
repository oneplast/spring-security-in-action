package security_in_action.ssia_ch18_resource.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import security_in_action.ssia_ch18_resource.domain.model.entiy.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    @Query("""
            SELECT w
            FROM Workout w
            WHERE w.user = :#{authentication.name}
            """)
    List<Workout> findAllByUser();
}
