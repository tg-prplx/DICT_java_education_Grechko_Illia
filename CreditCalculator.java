package CreditCalculator;

public class CreditCalculator {
    public static void main(String[] args) {
        String type = System.getProperty("type");
        String principalStr = System.getProperty("principal");
        String paymentStr = System.getProperty("payment");
        String periodsStr = System.getProperty("periods");
        String interestStr = System.getProperty("interest");

        if (type == null || (!type.equals("diff") && !type.equals("annuity"))) {
            System.out.println("Incorrect parameters");
            return;
        }

        if (interestStr == null || interestStr.isEmpty()) {
            System.out.println("Incorrect parameters");
            return;
        }

        Double principal = null;
        Double payment = null;
        Integer periods = null;
        double interest;

        try {
            if (principalStr != null) {
                if (principalStr.isEmpty()) {
                    System.out.println("Incorrect parameters");
                    return;
                }
                principal = Double.parseDouble(principalStr);
            }
            if (paymentStr != null) {
                if (paymentStr.isEmpty()) {
                    System.out.println("Incorrect parameters");
                    return;
                }
                payment = Double.parseDouble(paymentStr);
            }
            if (periodsStr != null) {
                if (periodsStr.isEmpty()) {
                    System.out.println("Incorrect parameters");
                    return;
                }
                periods = Integer.parseInt(periodsStr);
            }
            interest = Double.parseDouble(interestStr);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect parameters");
            return;
        }

        if (principal != null && principal <= 0
                || payment != null && payment <= 0
                || periods != null && periods <= 0
                || interest <= 0) {
            System.out.println("Incorrect parameters");
            return;
        }

        if (type.equals("diff")) {
            if (payment != null) {
                System.out.println("Incorrect parameters");
                return;
            }
            if (principal == null || periods == null) {
                System.out.println("Incorrect parameters");
                return;
            }
            diffPayments(principal, periods, interest);
        } else {
            int known = 0;
            if (principal != null) {
                known++;
            }
            if (payment != null) {
                known++;
            }
            if (periods != null) {
                known++;
            }
            if (known != 2) {
                System.out.println("Incorrect parameters");
                return;
            }
            annuityMode(principal, payment, periods, interest);
        }
    }

    private static void diffPayments(double principal, int periods, double interest) {
        double i = interest / (12 * 100);
        int sum = 0;
        for (int m = 1; m <= periods; m++) {
            double part = principal / periods;
            double dm = part + i * (principal - principal * (m - 1) / periods);
            int pay = (int) Math.ceil(dm);
            sum += pay;
            System.out.println("Month " + m + ": payment is " + pay);
        }
        int overpayment = sum - (int) principal;
        System.out.println("Overpayment = " + overpayment);
    }

    private static void annuityMode(Double principal, Double payment, Integer periods, double interest) {
        double i = interest / (12 * 100);
        if (principal == null) {
            double A = payment;
            int n = periods;
            double pow = Math.pow(1 + i, n);
            double base = i * pow / (pow - 1);
            double p = A / base;
            int result = (int) Math.floor(p);
            System.out.println("Your loan principal = " + result + "!");
        } else if (payment == null) {
            double P = principal;
            int n = periods;
            double pow = Math.pow(1 + i, n);
            double A = P * (i * pow / (pow - 1));
            int result = (int) Math.ceil(A);
            System.out.println("Your annuity payment = " + result + "!");
        } else {
            double P = principal;
            double A = payment;
            double base = A / (A - i * P);
            double n = Math.log(base) / Math.log(1 + i);
            int months = (int) Math.ceil(n);
            int years = months / 12;
            int remMonths = months % 12;

            StringBuilder sb = new StringBuilder();
            sb.append("It will take ");
            if (years > 0) {
                sb.append(years).append(" year");
                if (years > 1) {
                    sb.append("s");
                }
            }
            if (years > 0 && remMonths > 0) {
                sb.append(" and ");
            }
            if (remMonths > 0) {
                sb.append(remMonths).append(" month");
                if (remMonths > 1) {
                    sb.append("s");
                }
            }
            sb.append(" to repay this loan!");
            System.out.println(sb.toString());
        }
    }
}
