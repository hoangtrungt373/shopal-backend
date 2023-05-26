package vn.group24.shopalbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

/**
 * Base class for all Entities.
 *
 * @author ttg
 */
@MappedSuperclass
public abstract class AbstractEntity {

    protected static String getRawClassName(Class<?> baseClass) {
        Class<?> clazz = baseClass;
        while (isSyntheticClass(clazz)) {
            clazz = clazz.getSuperclass();
            if (clazz == null || clazz.equals(Object.class) || clazz.equals(AbstractEntity.class)) {
                throw new IllegalArgumentException(String.format("Can't retrieve non-synthetic class from class %s", baseClass));
            }
        }

        return clazz.getName();
    }

    protected static boolean isSyntheticClass(Class<?> clazz) {
        return clazz.isSynthetic()
                || org.hibernate.proxy.HibernateProxy.class.isAssignableFrom(clazz)
                || java.lang.reflect.Proxy.class.isAssignableFrom(clazz)
                || org.springframework.cglib.proxy.Proxy.class.isAssignableFrom(clazz);
    }

    @Transient
    private boolean transientHashCodeLeaked = false;

    @Transient
    private String rawClassName = getRawClassName(getClass());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isPersisted() {
        return this.id != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof AbstractEntity) {
            final AbstractEntity other = (AbstractEntity) obj;
            if (isPersisted() && other.isPersisted()) { // both entities are not new
                return getId().equals(other.getId()) && rawClassName.equals(other.rawClassName);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (!isPersisted()) { // is new or is in transient state.
            transientHashCodeLeaked = true;
            return -super.hashCode();
        }

        if (transientHashCodeLeaked) {
            return -super.hashCode();
        }
        return getId().hashCode();
    }
}
