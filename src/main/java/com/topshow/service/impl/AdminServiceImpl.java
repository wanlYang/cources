package com.topshow.service.impl;

import com.topshow.entity.StoreFront;
import com.topshow.service.TableCourcesService;
import com.topshow.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topshow.entity.Admin;
import com.topshow.entity.Result;
import com.topshow.mapper.AdminMapper;
import com.topshow.service.AdminService;
import com.topshow.utils.Base64Util;
import com.topshow.utils.MD5Util;

import java.util.List;

/**
 * 管理员操作服务实现类
 * @author Administrator
 *
 */
@Service
public class AdminServiceImpl implements AdminService{
    
    
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private TableCourcesService tableCourcesService;

    @Override
    public Result login(String admin_name, String admin_password) {
        if(!StringUtils.isNotBlank(admin_name) || !StringUtils.isNotBlank(admin_password)) {
            return new Result(-1,"用户名或密码不能为空!",0,null);
        }
        Admin admin = adminMapper.findAdminByNameAndPassword(admin_name,Base64Util.decoder(MD5Util.MD5Encode(admin_password)));
        if (admin == null) {
            return new Result(-2,"用户名或密码错误!",0,null);
        }
        return new Result(200,"管理员账号登录成功!",1,admin);
    }

    @Override
    public List<Admin> findAllAdmin() {

        return adminMapper.findAll();
    }

    @Override
    public Result add(Admin admin,String store) {


        StoreFront storeFront = tableCourcesService.findStoreById(store);
        if (storeFront.getAdmin() != null){
            return new Result(-1,"该店面已经有管理员!");
        }
        admin.setId(UUIDUtils.generateNumberUUID("ADMIN_ID"));
        admin.setPassword(Base64Util.decoder(MD5Util.MD5Encode(admin.getPassword())));
        admin.setDetail("管理员");
        Integer row = adminMapper.create(admin);
        tableCourcesService.insertStoreForAdmin(storeFront.getId(),admin.getId());
        return new Result(200,"添加成功!");
    }

    @Override
    public Integer edit(Admin admin, String store) {
        admin.setPassword(Base64Util.decoder(MD5Util.MD5Encode(admin.getPassword())));
        StoreFront storeFrontById = tableCourcesService.findStoreById(store);
        StoreFront oneFrontByAdminId = tableCourcesService.getOneFront(admin.getId());
        adminMapper.update(admin);
        if (oneFrontByAdminId == null && storeFrontById != null){
            tableCourcesService.updateStoreForAdmin(storeFrontById.getId(),admin.getId());
        }
        if (oneFrontByAdminId != null && storeFrontById == null){
            tableCourcesService.updateStoreForAdmin(oneFrontByAdminId.getId(),null);
        }
        if (oneFrontByAdminId != null && storeFrontById != null){
            if (!oneFrontByAdminId.getId().equals(storeFrontById.getId())){
                tableCourcesService.updateStoreForAdmin(oneFrontByAdminId.getId(),null);
                tableCourcesService.updateStoreForAdmin(storeFrontById.getId(),admin.getId());
            }
        }
        return 0;
    }

    @Override
    public Integer del(String admin_id) {
        StoreFront oneFrontByAdminId = tableCourcesService.getOneFront(admin_id);
        if (oneFrontByAdminId != null){
            tableCourcesService.updateStoreForAdmin(oneFrontByAdminId.getId(),null);
        }
        Integer row = adminMapper.del(admin_id);
        return row;
    }
}
