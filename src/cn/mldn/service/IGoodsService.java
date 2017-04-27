package cn.mldn.service;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import cn.mldn.vo.Goods;

public interface IGoodsService {
	/**
	 * 进行商品数据批量删除，调用IGoodsDAO.doRemoveBatch()方法
	 * 由于有外键级联删除关系 所以只删除主表即可 若没有则还要调用ITagDAO.doRemoveByGoods()方法
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(Set<Integer> ids) throws SQLException;
	
	/**
	 * 分页
	 * 1，key=allGoodss,value=所有商品信息
	 * 2，key=goodsCount,value= 商品数量信息
	 * 3,key=allItems , value = 所有商品分类数据（map集合）
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws SQLException
	 */
	public Map<String , Object> list(int currentPage,int lineSize,String column,String keyWord) throws SQLException;
	/**
	 * 分页
	 * 1，key=allGoodss,value=所有商品信息
	 * 2，key=goodsCount,value= 商品数量信息
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws SQLException
	 */
	public Map<String , Object> listWithBug(int currentPage,int lineSize,String column,String keyWord) throws SQLException;
	/**
	 * 进行商品数据添加前的信息查询  
	 * 1 查询所有商品分类信息 ， 调用IItemDaO.findAll()
	 * 2 查询所有商品标签信息 ， 调用ItagDAO.findAll()
	 * @return 返回数据包含内容
	 * 1 ， key = allItems , value = 所有item的分类信息
	 * 2 ，key = allTags , value = 所有商品的标签信息
	 * @throws SQLException
	 */
	public Map<String , Object> getAddPre() throws SQLException;
	/**
	 * 实现商品数据添加处理
	 * 1，首先保证商品有标签，以及商品有所属分类，商品价格要大于0；
	 * 2，要使用IGoodsDAO.findByName()方法判断商品的名称是否重复
	 * 3，如果商品名称不存在，使用IGoodsDAO.doCreate()进行信息保存
	 * 4，为了可以向goods_tag关系表中进行数据保存，一定要首先取得增长后的商品编号，调用IGoodsDAO.findCreateId()方法
	 * 5，调用ITagDAO.doCreateGoodsAndTag()方法保存商品和标签的对应信息
	 * @param vo  商品信息
	 * @param tids 所有的标签ID信息
	 * @return 保存成功返回true 否则返回false
	 * @throws SQLException
	 */
	public boolean add(Goods vo,Set<Integer> tids) throws SQLException;
	/**
	 * 进行商品数据添加前的信息查询  
	 * 1 查询所有商品分类信息 ， 调用IItemDaO.findAll()
	 * 2 查询所有商品标签信息 ， 调用ItagDAO.findAll()
	 * 3 根据编号查询商品信息，使用IGoods.findById()查询
	 * 4 查询商品已有的标签信息 使用ITagDAO.findAllByGoods()方法
	 * @return 返回数据包含内容
	 * 1 ， key = allItems , value = 所有item的分类信息
	 * 2 ，key = allTags , value = 所有商品的标签信息
	 * 3， key = goods,value=单个商品信息
	 * 4, key = goodsTag,value=已有商品信息标签
	 * @throws SQLException
	 */
	public Map<String , Object> getEditPre(int gid) throws SQLException;
	/**
	 * 实现商品数据添加处理
	 * 1，首先保证商品有标签，以及商品有所属分类，商品价格要大于0；
	 * 2，要使用IGoodsDAO.findByName()方法判断商品的名称是否重复 不对自己判断
	 * 3，如果商品名称不存在，使用IGoodsDAO.doUpdate()进行信息保存
	 * 4，商品与标签关系由于可能发生变化，则应该先删除
	 * 5，调用ITagDAO.doCreateGoodsAndTag()方法保存商品和标签的对应信息
	 * @param vo  商品信息
	 * @param tids 所有的标签ID信息
	 * @return 保存成功返回true 否则返回false
	 * @throws SQLException
	 */
	public boolean edit(Goods vo,Set<Integer> tids) throws SQLException;
}
