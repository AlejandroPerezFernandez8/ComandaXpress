package ComandaXpress.Ticket.Repository;

import ComandaXpress.Ticket.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query("SELECT t FROM Ticket t WHERE DATE(t.fechaHora) = :fecha")
    List<Ticket> findByFechaHora(@Param("fecha") Date fecha);

    List<Ticket> findByMesa_Numero(Integer numero);

    @Query("SELECT t FROM Ticket t WHERE DATE(t.fechaHora) = :fecha AND t.mesa.numero = :numero")
    List<Ticket> findByFechaHoraAndMesa_Numero(@Param("fecha") Date fecha, @Param("numero") Integer numero);
}
