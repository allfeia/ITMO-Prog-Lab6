package Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

public class IdGenerator implements Serializable {
    @Serial
    private static final long serialVersionUID = 215L;
    private static final Integer min = 1;

    private static final Integer max = 10000000;

    private static HashSet<Integer> idList = new HashSet<>();

    public static Integer generateId() {
        Integer id = (int) Math.floor(Math.random() * (max - min + 1)) + min;
        while (idList.contains(id)) {
            id = (int) Math.floor(Math.random() * (max - min + 1)) + min;
        }
        idList.add(id);
        return id;
    }

    public void addId(Movie movie){
        idList.add(movie.getId());
    }


    public HashSet<Integer> getIdList(){
        return idList;
    }
}
