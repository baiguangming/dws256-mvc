package cn.mldn.dao;

import java.sql.SQLException;

import cn.mldn.util.dao.IBaseDAO;
import cn.mldn.vo.Goods;

public interface IGoodsDAO extends IBaseDAO<Integer, Goods> {
	
	/**
	 * 增加商品信息  名字不许重复 所以要一个根据名称查询商品的方法
	 * 
	 * @param name 商品名
	 * @return	如果查找到以Goods对向形式返回 否则返回null
	 * @throws SQLException
	 */
	public Goods findByName(String name) throws SQLException;
		/**
		 * 通过goods_seq序列取得增长后的序列内容
		 * @return
		 * @throws SQLException
		 */
	public Integer findCreateId() throws SQLException;
	
}
