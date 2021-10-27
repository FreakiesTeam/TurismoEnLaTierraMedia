package dao;

public interface GenericDAO<T> {

	public int insert(T t);
	public int update(T t);
	public int delete(T t);
}
