using InventoryManagement.DTO;
using Microsoft.EntityFrameworkCore;


namespace InventoryManagement.Services
{
    public class InventoryServices : IInventoryService

    {
       
        private readonly DataContext _context;

        public InventoryServices(DataContext context)
        {
            _context = context;
        }

        public async Task<List<InventoryModel>> AddProduct(InventoryModel product)
        {
            var products = await _context.Inventory.ToListAsync();
            _context.Inventory.Add(product);
            await _context.SaveChangesAsync();
            return products;
        }

        public async Task<List<InventoryModel>> DeleteProduct(long ID)
        {
            var products = await _context.Inventory.ToListAsync();
            var product = await _context.Inventory.FindAsync(ID);
            if (product == null)
            {
                return null;
            }
            _context.Inventory.Remove(product);
            await _context.SaveChangesAsync();
            return products;
        }

        public async Task<List<InventoryModel>> GetAllProducts()
        {
            var products = await _context.Inventory.ToListAsync();
            return products;
        }

        public async Task<InventoryModel> GetsingleProducts(long PId)
        {
            var product = await _context.Inventory.FindAsync(PId);

            if (product == null)
            {
                return null;
            }
            return product;
        }

        public async Task<List<InventoryModel>> UpdateProducts(long PId, InventoryModel request)
        {
            var products = await _context.Inventory.ToListAsync();
            var product = await _context.Inventory.FindAsync(PId);

           if (product == null)
            {
                return null;
            }

            product.Product_Name = request.Product_Name;
            product.Product_Brand = request.Product_Brand;
            product.Price = request.Price;
            product.Available_Quantity = request.Available_Quantity;

            await _context.SaveChangesAsync();

            return products;
        }
        public async Task<InventoryModel> CancelOrder(long PID, int Qty)
        {

            var product = await _context.Inventory.FindAsync(PID);

            if (product == null)
            {
                return null;
            }

            product.Available_Quantity += Qty;

            await _context.SaveChangesAsync();

            return product;
        }
        public async Task<OrderDTO> PlaceOrder(long ID,int Qty)
        {
            
            var product =await _context.Inventory.FindAsync(ID);
            if (product == null)
            {
                return null;
            }
            var Quantity = product.Available_Quantity - Qty;
            OrderDTO ODTO = new OrderDTO();
            if (Quantity> 0)
            {
                var Newprice = product.Price*Qty;


                ODTO.PId = ID;
                ODTO.Price = Newprice;
                ODTO.Available_Quantity = Quantity;
                
                        
            }
            
            return ODTO;
        }
    }
}
