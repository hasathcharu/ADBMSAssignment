using InventoryManagement.Models;
using InventoryManagement.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace InventoryManagement.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class InventoryController : ControllerBase
    {
        private readonly IInventoryService _inventoryService;

        public InventoryController(IInventoryService inventoryService)
        {
            _inventoryService = inventoryService;
        }
        [HttpGet]

        public async Task<ActionResult<List<InventoryModel>>> GetAllProducts()
        {
            return await _inventoryService.GetAllProducts();
        }

        [HttpGet ("{PID}")]

        public async Task<ActionResult<InventoryModel>> GetsingleProducts(int PID)
        {
            var result = await _inventoryService.GetsingleProducts(PID);
            if (result is null)
            {
                return NotFound("Product not found");
            }
            return Ok(result);
        }

        [HttpPost]

        public async Task<ActionResult<List<InventoryModel>>> AddProduct(InventoryModel product)
        {
            var result = await _inventoryService.AddProduct(product);
            return Ok(result);
        }

          [HttpPut("{PID}")]

          public async Task<ActionResult<List<InventoryModel>>> UpdateProducts(long PID, InventoryModel request)
         {
           var result = await _inventoryService.UpdateProducts(PID, request);
           if (result is null)
           {
              return NotFound("Product not found");
           }
           return Ok(result);
         }

        [HttpPut("CancelOrder/{PID},{Qty}")]

        public async Task<ActionResult<InventoryModel>> CancelOrder(long PID, int Qty)
        {
            var result = await _inventoryService.CancelOrder(PID, Qty);
            if (result is null)
            {
                return NotFound("Product not found");
            }
            return Ok(result);
        }

        [HttpPut("PlaceOrder/{PID},{Qty}")]

        public async Task<ActionResult<InventoryModel>> PlaceOrder(long PID, int Qty)
        {
            var result = await _inventoryService.PlaceOrder(PID, Qty);
            if (result is null)
            {
                return NotFound("Product not found");
            }
            return Ok(result);
        }

        [HttpDelete ("{PID}")]

        public async Task<ActionResult<List<InventoryModel>>> DeleteProduct( long ID)
        {
            var result = await _inventoryService.DeleteProduct(ID);
            if(result is null) 
            {
                return NotFound("Product not found");
            }
            return Ok(result);
        }
    }
}
