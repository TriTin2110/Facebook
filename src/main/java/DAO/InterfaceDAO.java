package DAO;

import java.util.List;

public interface InterfaceDAO<T> {
	public int add(T t);

	public int remove(T t);

	public int update(T t);

	public T selectById(T t);

	public List<T> selectAll();
}
