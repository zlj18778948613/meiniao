package com.atguigu.crud.service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * @作者: zhulinjia
     * @时间: 2022/8/16 14:41
     * @Return: 返回 true 代表当前姓名可以用  返回false 代表用户名不可用
     * @Trans:  用户名
     * 校验用户姓名是否可用
     */
    public boolean checkUser(String empName){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count==0;
    }


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
