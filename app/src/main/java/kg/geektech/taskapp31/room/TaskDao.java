package kg.geektech.taskapp31.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kg.geektech.taskapp31.models.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Insert
    void insert(Task task);

    @Query("SELECT * FROM task ORDER BY title ASC")
    LiveData<List<Task>> sortByAsc();

    @Delete
    void remove(Task task);

}
