package reminator.RemiBot.commands.birthday;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

public record Birthday(int day, Month month, String name, String authorId) implements Serializable {
    static long serialVersionUID = -5086145906336594004L;
    @Override
    public String toString() {
        return "Anniversaire{" +
                "day=" + day +
                ", month=" + month +
                ", name='" + name + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }

    public boolean isToday() {
        LocalDate now = LocalDate.now();
        return day == now.getDayOfMonth() && month == now.getMonth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Birthday birthday = (Birthday) o;

        if (!Objects.equals(name, birthday.name)) return false;
        return Objects.equals(authorId, birthday.authorId);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        return result;
    }
}
