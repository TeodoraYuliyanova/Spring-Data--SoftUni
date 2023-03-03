enum Queries {
    ;

    static final String GET_ALL_TOWNS = "SELECT t FROM Town t WHERE LENGTH(t.name) <= 5";

    static final String CONTAINS_EMPLOYEE_NAME = "SELECT COUNT(e) FROM Employee e WHERE e.firstName = :fn AND e.lastName = :ln";

    static final String ALL_EMPLOYEES_WITH_SALARY_OVER_50000 = "SELECT e.firstName FROM Employee e WHERE e.salary > 50000";

    static final String EMPLOYEES_FROM_DEPARTMENT = "SELECT e FROM Employee e WHERE e.department.name = :dn ORDER BY e.salary,e.id";
    static final String UPDATE_EMPLOYEE_ADDRESS = "UPDATE Employee e SET e.address = :newAddress WHERE e.lastName = :ln";

    static final String GET_EMPLOYEE_BY_ID = "SELECT e FROM Employee e WHERE e.id = :id";

    static final String GET_LATEST_10_PROJECTS = "SELECT p FROM Project p ORDER BY p.startDate DESC";

    static final String UPDATE_SALARIES = "UPDATE Employee e SET e.salary = e.salary * 1.12 WHERE e.department.id IN (1,2,4,11)";

    static final String EMPLOYEES_WITH_UPDATED_SALARY = "SELECT e FROM Employee e WHERE e.department.id IN (1,2,4,11)";

    static final String EMPLOYEES_WITH_FIRST_FOUND_BY_PATTERN = "SELECT e FROM Employee e WHERE e.firstName LIKE :fn";

    static final String DEPARTMENTS_MAX_SALARY = "SELECT e FROM Employee e WHERE e.salary NOT BETWEEN 30000 AND 70000 " +
            "GROUP BY e.department";

    static final String DELETE_ADDRESS_TOWN = "DELETE FROM Address a WHERE a.town.name = :tn";


}