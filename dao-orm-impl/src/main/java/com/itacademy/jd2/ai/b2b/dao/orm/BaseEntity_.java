package com.itacademy.jd2.ai.b2b.dao.orm;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.itacademy.jd2.ai.b2b.dao.orm.model.BaseEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

    public static volatile SingularAttribute<BaseEntity, Date> created;
    public static volatile SingularAttribute<BaseEntity, Integer> id;
    public static volatile SingularAttribute<BaseEntity, Date> updated;

}
