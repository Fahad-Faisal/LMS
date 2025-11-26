function myFunction() {
  var x = document.getElementById("exampleInputPassword1");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }

    $(document).ready(function() {
        // When edit button is clicked
        $('.editBtn').on('click', function() {
            // Get the table row (tr) that contains the clicked edit button
            var row = $(this).closest('tr');

            // Extract data from the table row
            var id = row.find('th:first').text().trim();
            var publisherName = row.find('td:eq(0)').text().trim();
            var address = row.find('td:eq(1)').text().trim();
            var description = row.find('td:eq(2)').text().trim();

            // Populate the modal form fields
            $('#pubName').val(publisherName);
            $('#address').val(address);
            $('#description').val(description);

            // Optional: Update the form action URL with the specific ID
            var form = $('form[th\\:action="@{edit}"]');
            var currentAction = form.attr('th:action') || form.attr('action');
            var newAction = currentAction.replace('{id}', id);
            form.attr('action', newAction);
        });

        // Optional: Clear form when modal is hidden
        $('#staticBackdrop').on('hidden.bs.modal', function() {
            $('#pubName').val('');
            $('#address').val('');
            $('#description').val('');
        });
    });
}