package DAO;

import java.util.List;

public interface InterfaceDAO<T> {
	public int add(T t);

	public int remove(T t);

	public int update(T t);

	public T selectById(String t);

	public List<T> selectAll();
}
