package enums;

/**
 * Enum to specify the Fields of GET response.
 */
public enum ResponseFields {
    STATUS{
        @Override
        public String toString() {
            return "status";
        }
    },TOTAL_SALARIES{
        @Override
        public String toString() {
            return "totalSalaries";
        }
    },EMPLOYEES_COUNT{
        @Override
        public String toString() {
            return "employeesCount";
        }
    },MIN_SALARY{
        @Override
        public String toString() {
            return "minSalary";
        }
    },ITEMS{
        @Override
        public String toString() {
            return "items";
        }
    },
    MAX_SALARY{
        @Override
        public String toString() {
            return "maxSalary";
        }
    }
}
