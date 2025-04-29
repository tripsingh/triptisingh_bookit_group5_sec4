// Helper function to format date-time
function formatDateTime(dateTimeString) {
    const options = {
        year: 'numeric', month: 'short', day: 'numeric',
        hour: '2-digit', minute: '2-digit',
        hour12: true
    };
    const date = new Date(dateTimeString);
    return date.toLocaleString('en-US', options);
}

async function fetchBookings() {
    try {
        const response = await fetch('http://localhost:8080/api/bookings');
        const data = await response.json();

        const bookingList = document.getElementById('booking-list');
        bookingList.innerHTML = ''; // Clear existing content

        if (data.length === 0) {
            bookingList.innerHTML = '<p>No bookings found!</p>';
            return;
        }

        data.forEach(booking => {
            const bookingCard = document.createElement('div');
            bookingCard.classList.add('booking-card');

            bookingCard.innerHTML = `
                <h3>Booking #${booking.bookingId}</h3>
                <p><strong>${booking.user.firstName} ${booking.user.lastName}</strong> booked <strong>${booking.room.roomName}</strong></p>
                <p><strong>From:</strong> ${formatDateTime(booking.startTime)}</p>
                <p><strong>To:</strong> ${formatDateTime(booking.endTime)}</p>
                <p><strong>Purpose:</strong> ${booking.purpose}</p>
                <p><strong>Status:</strong> ${booking.bookingStatus}</p>
                <button onclick="deleteBooking(${booking.bookingId})" class="delete-btn">Delete Booking</button>
            `;

            bookingList.appendChild(bookingCard);
        });
    } catch (error) {
        console.error('Error fetching bookings:', error);
    }
}

async function deleteBooking(bookingId) {
    if (!confirm('Are you sure you want to delete this booking?')) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/bookings/${bookingId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Booking Deleted!');
            fetchBookings(); // Re-fetch and update the list
        } else {
            alert('Failed to delete booking!');
        }
    } catch (error) {
        console.error('Error deleting booking:', error);
    }
}

// Fetch bookings when the page loads
window.onload = function() {
    fetchBookings();
};
