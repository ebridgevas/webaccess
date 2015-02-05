package zw.co.telecel.vas.dto;

/**
 * @author david@tekeshe.com
 */

import zw.co.telecel.vas.model.BalanceDTO;

import java.util.List;

public class BalanceResponseDTO {

    private List<BalanceDTO> balances;
    private String classOfService;

    public List<BalanceDTO> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceDTO> balances) {
        this.balances = balances;
    }

    public String getClassOfService() {
        return classOfService;
    }

    public void setClassOfService(String classOfService) {
        this.classOfService = classOfService;
    }
}
