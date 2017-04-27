package cn.mldn.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.mldn.dao.IGoodsDAO;
import cn.mldn.dao.IItemDAO;
import cn.mldn.dao.ITagDAO;
import cn.mldn.service.IGoodsService;
import cn.mldn.service.abs.AbstractService;
import cn.mldn.util.factory.Factory;
import cn.mldn.vo.Goods;

public class GoodsServiceImpl extends AbstractService implements IGoodsService {

	@Override
	public Map<String, Object> getAddPre() throws SQLException {
		Map<String,Object> map = new HashMap<String,Object>();
		IItemDAO iItemDAO = Factory.getDAOInstance("item.dao");
		ITagDAO itTagDAO = Factory.getDAOInstance("tag.dao");
		map.put("allItems", iItemDAO.findAll());
		map.put("allTags", itTagDAO.findAll());
		return map;
	}

	@Override
	public boolean add(Goods vo, Set<Integer> tids) throws SQLException {
		if(vo.getIid()==null||vo.getIid().equals(0)||vo.getPrice()<=0
				|| vo.getPhoto()== null || "".equals(vo.getPhoto())){
			return false;
		}
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		if(goodsDAO.findByName(vo.getName())==null){ //名称不重复 可以保存
			if(goodsDAO.doCreate(vo)){ //保存商品信息
				Integer gid = goodsDAO.findCreateId();  //取得增长后的编号
				ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
				tagDAO.doCreateGoodsAndTag(gid, tids);
				return true;
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> listWithBug(int currentPage, int lineSize, String column, String keyWord)
			throws SQLException {
		Map<String , Object> map = new HashMap<String ,Object>();
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		if(column==null||"".equals(column) || keyWord==null || "".equals(keyWord)){
			map.put("allGoodss", goodsDAO.findAllSplit(currentPage, lineSize));
			map.put("goodsCount", goodsDAO.getAllCount());
		}else {
			map.put("allGoodss", goodsDAO.findAllSplit(currentPage, lineSize, column, keyWord));
			map.put("goodsCount",goodsDAO.getAllCount(column, keyWord));
		}
		return map;
	}

	@Override
	public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws SQLException {
		Map<String , Object> map = new HashMap<String ,Object>();
		IItemDAO itemDAO = Factory.getDAOInstance("item.dao");
		map.put("allItems", itemDAO.findAllForMap());
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		if(column==null||"".equals(column) || keyWord==null || "".equals(keyWord)){
			map.put("allGoods", goodsDAO.findAllSplit(currentPage, lineSize));
			map.put("goodsCount", goodsDAO.getAllCount());
		}else {
			map.put("allGoods", goodsDAO.findAllSplit(currentPage, lineSize, column, keyWord));
			map.put("goodsCount",goodsDAO.getAllCount(column, keyWord));
		}
		return map;
	}

	@Override
	public Map<String, Object> getEditPre(int gid) throws SQLException {
		IItemDAO iItemDAO = Factory.getDAOInstance("item.dao");
		ITagDAO itTagDAO = Factory.getDAOInstance("tag.dao");
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allItems", iItemDAO.findAll());
		map.put("allTags", itTagDAO.findAll());
		map.put("goods", goodsDAO.findById(gid));
		map.put("goodsTag", itTagDAO.findAllByGoods(gid));
		return map;
	}

	@Override
	public boolean edit(Goods vo, Set<Integer> tids) throws SQLException {
		if(vo.getIid()==null||vo.getIid().equals(0)||vo.getPrice()<=0){
			return false;
		}
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		Goods nameGoods = goodsDAO.findByName(vo.getName());
		if(nameGoods==null || vo.getName().equals(nameGoods.getName())){ 
			if(goodsDAO.doUpdate(vo)){ //修改商品信息
				ITagDAO tagDAO = Factory.getDAOInstance("tag.dao");
				tagDAO.doRemoveGoodsAndTag(vo.getGid());
				tagDAO.doCreateGoodsAndTag(vo.getGid(), tids);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(Set<Integer> ids) throws SQLException {
		if(ids == null || ids.size()==0){
			return false;
		}
		IGoodsDAO goodsDAO = Factory.getDAOInstance("goods.dao");
		return goodsDAO.doRemoveBatch(ids);
	}

}
