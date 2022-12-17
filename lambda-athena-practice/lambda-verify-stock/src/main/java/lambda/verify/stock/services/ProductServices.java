package lambda.verify.stock.services;

import java.util.ArrayList;
import java.util.List;

import lambda.verify.stock.builders.querybuilder.QueryBuilder;
import lambda.verify.stock.models.Product;
import lambda.verify.stock.models.ProductInput;
import lambda.verify.stock.models.Response;

public class ProductServices {
    private AthenaService athenaService;
    private List<Response> responseList = new ArrayList<>();

    public ProductServices(AthenaService athenaService){
        this.athenaService = athenaService;    
    }

    public List<Response> getProductsStock(List<ProductInput> products){
        for(ProductInput p: products){
            String lookUpValue = "'" + p.getProdId() + "'";
            String constraint = "id" + "=" + lookUpValue;
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.select("name","stock", "id");
            queryBuilder.from("products");
            queryBuilder.where(constraint);
            String queryString = queryBuilder.getQuery();
            System.out.println(queryString);
            AthenaOrchestrator orchestrator = new AthenaOrchestrator(queryString, athenaService);
            List<Product> result = orchestrator.execute();
            for(Product prod: result){
                if(prod.getStock() > p.getQty()){
                    Response response = new Response();
                    response.setProdId(prod.getId());
                    response.setStatus("OK");
                    response.setMessage("");
                    responseList.add(response);
                } else {
                    Response response = new Response();
                    response.setProdId(prod.getId());
                    response.setStatus("Failed");
                    response.setMessage("Product " + p.getName() + "stock is " + prod.getStock());
                    responseList.add(response);
                }
            }
        }
        
        return this.responseList;
    }
}
