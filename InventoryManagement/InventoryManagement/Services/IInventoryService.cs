using InventoryManagement.DTO;

namespace InventoryManagement.Services
{
    public interface IInventoryService
    {
        Task<List<InventoryModel>>? GetAllProducts();

        Task<InventoryModel> GetsingleProducts(long PID);

        Task<List<InventoryModel>> AddProduct(InventoryModel product);

        Task<List<InventoryModel>>? UpdateProducts(long PID, InventoryModel request);

        Task<List<InventoryModel>>? DeleteProduct(long ID);
        Task<OrderDTO> PlaceOrder(long ID, int Qty);
        Task<InventoryModel> CancelOrder(long PID, int Qty);
        
    }
}
