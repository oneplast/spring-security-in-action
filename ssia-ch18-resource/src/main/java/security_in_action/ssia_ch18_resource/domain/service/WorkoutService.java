package security_in_action.ssia_ch18_resource.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import security_in_action.ssia_ch18_resource.domain.model.entiy.Workout;
import security_in_action.ssia_ch18_resource.domain.repository.WorkoutRepository;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @PreAuthorize("@securityUtils.isOwner(authentication, #workout.user)")
    public void saveWorkout(Workout workout) {
        workoutRepository.save(workout);
    }

    public List<Workout> findWorkouts() {
        return workoutRepository.findAllByUser();
    }

    public void deleteWorkout(Integer id) {
        workoutRepository.deleteById(id);
    }
}
