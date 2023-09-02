using Microsoft.EntityFrameworkCore;
using System.Reflection.Emit;

namespace InventoryManagement.Data
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) :base(options)
        {
            
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            base.OnConfiguring(optionsBuilder);
            optionsBuilder.UseSqlServer("Server= DESKTOP-JN3625D;Database=Inventory_DB;Trusted_Connection=true;TrustServerCertificate=true;");    
        }

        public DbSet<InventoryModel> Inventory { get; set; }
    }
}
