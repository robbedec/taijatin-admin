package domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public class ActivitiesPK implements Serializable {
    private int userId;
    private int activityId;

    @Column(name = "UserId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "ActivityId")
    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivitiesPK that = (ActivitiesPK) o;
        return userId == that.userId &&
                activityId == that.activityId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, activityId);
    }
}
