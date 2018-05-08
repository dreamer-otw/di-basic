package com.dreamer.basic.backstage.sys.service;

import com.alibaba.fastjson.JSON;
import com.sys.repository.dao.SysConfigDao;
import com.sys.repository.entity.SysConfigEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年12月4日 下午6:49:01
 */
@Service
public class SysConfigService {
	@Autowired
	private SysConfigDao sysConfigDao;

	/**
	 * 保存配置信息
	 */
	public void save(SysConfigEntity config) {
		sysConfigDao.save(config);
	}

	/**
	 * 更新配置信息
	 */
	public void update(SysConfigEntity config) {
		sysConfigDao.update(config);
	}

	/**
	 * 根据key，更新value
	 */
	public void updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
	}

	/**
	 * 删除配置信息
	 */
	public void deleteBatch(String[] ids) {
		sysConfigDao.deleteBatch(ids);
	}

	/**
	 * 获取List列表
	 */
	public List<SysConfigEntity> queryList(Map<String, Object> map) {
		return sysConfigDao.queryList(map);
	}

	/**
	 * 获取总记录数
	 */
	public int queryTotal(Map<String, Object> map) {
		return sysConfigDao.queryTotal(map);
	}

	/**
	 * 根据ID，获取系统配置信息
	 * @param id 配置信息ID
	 * @return
	 */
	public SysConfigEntity queryObject(String id) {
		return sysConfigDao.queryObject(id);
	}

	/**
	 * 根据key，获取配置的value值
	 * @param key key
	 * @param defaultValue 缺省值
	 */
	public String getValue(String key, String defaultValue) {
		String value = sysConfigDao.queryByKey(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * 根据key，获取value的Object对象
	 * @param key key
	 * @param clazz Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz) throws Exception {
		String value = getValue(key, null);
		if (StringUtils.isNotBlank(value)) {
			return JSON.parseObject(value, clazz);
		}

		return clazz.newInstance();
	}
}
