package ComandaXpress.DTO;

import ComandaXpress.TicketDetalle.Model.TicketDetalle;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class TicketDetalleDTO {
    public TicketDetalleDTO(){}
    private long idTicket;
    private List<ProductoCantidad> productos;
    @Data
    public static class ProductoCantidad {
        private long idProducto;
        private List<Integer> cantidades;
        public ProductoCantidad(long idProducto) {
            this.idProducto = idProducto;
            this.cantidades = new ArrayList<>();
        }
        public void addCantidad(int cantidad) {
            this.cantidades.add(cantidad);
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
            long productoId = detalle.getProducto().getProductoId();
            int cantidad = detalle.getCantidad();

            ProductoCantidad producto = resultado.computeIfAbsent(ticketId, k -> new HashMap<>())
                    .computeIfAbsent(productoId, ProductoCantidad::new);
            producto.addCantidad(cantidad);
        }
        return convertirListaAModeloDTO(resultado);
    }
    public static TicketDetalleDTO agruparDetalles(TicketDetalle detalle) {
        Map<Long, Map<Long, ProductoCantidad>> resultado = new HashMap<>();
        long ticketId = detalle.getTicket().getTicketId();
        long productoId = detalle.getProducto().getProductoId();
        int cantidad = detalle.getCantidad();

        ProductoCantidad producto = resultado.computeIfAbsent(ticketId, k -> new HashMap<>())
                .computeIfAbsent(productoId, ProductoCantidad::new);
        producto.addCantidad(cantidad);
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



}
