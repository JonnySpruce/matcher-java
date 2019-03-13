package com.scottlogic.training.matcher;

import com.scottlogic.training.market.Market;
import com.scottlogic.training.orders.OrderDTO;
import com.scottlogic.training.trades.Trade;

public class Matcher {
    private Market market;
    private OrderDTO currentOrder;

    public Matcher(Market market, OrderDTO currentOrder) {
        this.market = market;
        this.currentOrder = currentOrder;
    }

    public Market getMarket() {
        return market;
    }

    public void match(){

        if(!currentOrder.getOrderType() && market.getBuyOrders().size() != 0){
            for(int i = 0; i < market.getBuyOrders().size(); i++){
                if(currentOrder.getPrice() <= market.getBuyOrders(i).getPrice() && currentOrder.getQuantityRemaining() != 0){
                    if(currentOrder.getQuantityRemaining() < market.getBuyOrders(i).getQuantityRemaining()){
                        market.getBuyOrders(i).setQuantityRemaining(currentOrder.getQuantityRemaining());
                        market.addTrade(new Trade(market.getBuyOrders(i).getPrice(), currentOrder.getQuantityRemaining()));
                        currentOrder.setQuantityRemaining(0);
                    }
                    else if (currentOrder.getQuantityRemaining() >= market.getBuyOrders(i).getQuantityRemaining()){
                        currentOrder.setQuantityRemaining(market.getBuyOrders(i).getQuantityRemaining());
                        market.addTrade(new Trade(market.getBuyOrders(i).getPrice(), currentOrder.getQuantityRemaining()));
                        market.getBuyOrders(i).setQuantityRemaining(0);
                        market.removeOrder(market.getBuyOrders(i));
                        i -= 1;
                    }
                }
            } if (currentOrder.getQuantityRemaining() !=0 && market.getBuyOrders().size() != 0){
                market.addOrder(currentOrder);
            }
        }
        if(!currentOrder.getOrderType() && market.getBuyOrders().size() == 0 && currentOrder.getQuantityRemaining() !=0){
            market.addOrder(currentOrder);
        }

        if(currentOrder.getOrderType() && market.getSellOrders().size() != 0){
            for(int i = 0; i < market.getSellOrders().size(); i++){
                if(currentOrder.getPrice() <= market.getSellOrders(i).getPrice() && currentOrder.getQuantityRemaining() != 0){
                    if(currentOrder.getQuantityRemaining() < market.getSellOrders(i).getQuantityRemaining()){
                        market.getSellOrders(i).setQuantityRemaining(currentOrder.getQuantityRemaining());
                        market.addTrade(new Trade(market.getSellOrders(i).getPrice(), currentOrder.getQuantityRemaining()));
                        currentOrder.setQuantityRemaining(0);
                    }
                    else if (currentOrder.getQuantityRemaining() >= market.getSellOrders(i).getQuantityRemaining()){
                        currentOrder.setQuantityRemaining(market.getSellOrders(i).getQuantityRemaining());
                        market.getSellOrders(i).setQuantityRemaining(0);
                        market.removeOrder(market.getSellOrders(i));
                        i -= 1;
                    }
                }
            } if (currentOrder.getQuantityRemaining() !=0 && market.getSellOrders().size() != 0){
                market.addOrder(currentOrder);
            }
        }
        if(currentOrder.getOrderType() && market.getSellOrders().size() == 0 && currentOrder.getQuantityRemaining() !=0){
            market.addOrder(currentOrder);
        }

    }


}
