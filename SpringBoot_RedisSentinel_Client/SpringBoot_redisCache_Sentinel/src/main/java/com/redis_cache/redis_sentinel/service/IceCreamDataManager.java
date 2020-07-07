package com.redis_cache.redis_sentinel.service;

import com.redis_cache.redis_sentinel.model.IceCream;
import java.util.List;

public interface IceCreamDataManager {
    List<IceCream> getAllIceCream();
    IceCream getIceCream(String id);
    IceCream addIceCream(IceCream iceCream);
    IceCream updateCream(IceCream iceCream, String id);
    void deleteIceCream(String id);
}
