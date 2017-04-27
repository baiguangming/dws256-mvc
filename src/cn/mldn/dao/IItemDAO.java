package cn.mldn.dao;

import java.sql.SQLException;
import java.util.Map;

import cn.mldn.util.dao.IBaseDAO;
import cn.mldn.vo.Item;

public interface IItemDAO extends IBaseDAO<Integer, Item> {
	/**
	 * 以map几何形式返回所有商品分类数据
	 * @return key=商品编号  value=分类名称
	 * @throws SQLException
	 */
	public Map<Integer, String> findAllForMap() throws SQLException;
}
