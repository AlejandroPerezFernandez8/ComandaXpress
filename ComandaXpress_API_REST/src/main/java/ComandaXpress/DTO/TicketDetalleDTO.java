package ComandaXpress.DTO;

import ComandaXpress.Producto.Model.Producto;
import ComandaXpress.Ticket.Model.Ticket;
import ComandaXpress.TicketDetalle.Model.TicketDetalle;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TicketDetalleDTO {
    public TicketDetalleDTO() {}
    private long idTicket;
    private List<ProductoCantidad> productos;

    @Data
    @NoArgsConstructor
    public static class ProductoCantidad {
        private Producto producto;
        private int cantidad;

        public ProductoCantidad(Producto producto) {
            this.producto = producto;
            this.cantidad = 0;
        }

        public void addCantidad(int cantidad) {
            this.cantidad += cantidad;
        }
    }

    public TicketDetalleDTO(long idTicket) {
        this.idTicket = idTicket;
        this.productos = new ArrayList<>();
    }

    public void addProducto(ProductoCantidad producto) {
        this.productos.add(producto);
    }

    public static List<TicketDetalleDTO> agruparDetallesLista(List<TicketDetalle> detalles) {
        Map<Long, Map<Long, ProductoCantidad>> resultado = new HashMap<>();

        for (TicketDetalle detalle : detalles) {
            long ticketId = detalle.getTicket().getTicketId();
            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();

            ProductoCantidad productoCantidad = resultado.computeIfAbsent(ticketId, k -> new HashMap<>())
                    .computeIfAbsent(producto.getProductoId(), k -> new ProductoCantidad(producto));
            productoCantidad.addCantidad(cantidad);
        }
        return convertirListaAModeloDTO(resultado);
    }

    public static TicketDetalleDTO agruparDetalles(TicketDetalle detalle) {
        Map<Long, Map<Long, ProductoCantidad>> resultado = new HashMap<>();
        long ticketId = detalle.getTicket().getTicketId();
        Producto producto = detalle.getProducto();
        int cantidad = detalle.getCantidad();

        ProductoCantidad productoCantidad = resultado.computeIfAbsent(ticketId, k -> new HashMap<>())
                .computeIfAbsent(producto.getProductoId(), k -> new ProductoCantidad(producto));
        productoCantidad.addCantidad(cantidad);
        return convertirAModeloDTO(resultado);
    }

    private static List<TicketDetalleDTO> convertirListaAModeloDTO(Map<Long, Map<Long, ProductoCantidad>> resultado) {
        List<TicketDetalleDTO> listaDTOs = new ArrayList<>();
        for (Map.Entry<Long, Map<Long, ProductoCantidad>> ticketEntry : resultado.entrySet()) {
            TicketDetalleDTO ticketDTO = new TicketDetalleDTO(ticketEntry.getKey());
            for (ProductoCantidad producto : ticketEntry.getValue().values()) {
                ticketDTO.addProducto(producto);
            }
            listaDTOs.add(ticketDTO);
        }
        return listaDTOs;
    }

    private static TicketDetalleDTO convertirAModeloDTO(Map<Long, Map<Long, ProductoCantidad>> resultado) {
        TicketDetalleDTO ticketDetalleDTO = new TicketDetalleDTO();
        for (Map.Entry<Long, Map<Long, ProductoCantidad>> ticketEntry : resultado.entrySet()) {
            ticketDetalleDTO.setIdTicket(ticketEntry.getKey());
            for (ProductoCantidad producto : ticketEntry.getValue().values()) {
                ticketDetalleDTO.addProducto(producto);
            }
        }
        return ticketDetalleDTO;
    }

    public static List<TicketDetalle> convertDTOToTicketDetalles(TicketDetalleDTO ticketDetalleDTO) {
        List<TicketDetalle> detalles = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketDetalleDTO.getIdTicket());

        for (ProductoCantidad productoCantidad : ticketDetalleDTO.getProductos()) {
            Producto producto = productoCantidad.getProducto();

            TicketDetalle detalle = new TicketDetalle();
            detalle.setTicket(ticket);
            detalle.setProducto(producto);
            detalle.setCantidad(productoCantidad.getCantidad());
            detalles.add(detalle);
        }
        return detalles;
    }
}
