package com.atguigu.crud.service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     * */
    public List<Employee> getAll(){
        return employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * @作者: zhulinjia
     * @时间: 2022/8/16 10:11
     * @Return:
     * @Trans:
     * 保存员工的方法
     */

    public void saveEmp(Employee employee){
        employeeMapper.insertSelective(employee);
    }
}
