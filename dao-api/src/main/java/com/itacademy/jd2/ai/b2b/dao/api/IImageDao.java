package com.itacademy.jd2.ai.b2b.dao.api;

import java.util.List;

import com.itacademy.jd2.ai.b2b.dao.api.filter.ImageFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;

public interface IImageDao extends BaseDao<IImage, Integer> {

    long getCount(ImageFilter filter);

    List<IImage> find(ImageFilter filter);

}
