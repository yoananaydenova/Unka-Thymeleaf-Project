let dropdownDebitNumber = jSuites.dropdown(document.getElementById('dropdownDebitNumber'), {
    url: '/charts/api',
    autocomplete: true,
    lazyLoading: true,
    multiple: false,
    onload: function () {
        let dbValue = document.getElementById('searchDtInput').value;
        let dropdownData = dropdownDebitNumber.getData();
        let searchedChart = dropdownData.find(data => {
            if (dbValue) {
               return data.value == dbValue
            }
        });
        dropdownDebitNumber.setValue(dbValue);
        dropdownDebitNumber.innerHTML(searchedChart.name);
    },
    onclose: function () {
        document.getElementById('searchDtInput').value = dropdownDebitNumber.getValue();
    },
    width: '590px'
});


let dropdownCreditNumber = jSuites.dropdown(document.getElementById('dropdownCreditNumber'), {
    url: '/charts/api',
    autocomplete: true,
    lazyLoading: true,
    multiple: false,
    onload: function () {
        let dbValue = document.getElementById('searchCtInput').value;
        let dropdownData = dropdownCreditNumber.getData();
        let searchedChart = dropdownData.find(data => {
            if (dbValue) {
                return data.value == dbValue
            }
        });
        dropdownCreditNumber.setValue(dbValue);
        dropdownCreditNumber.innerHTML(searchedChart.name);
    },
    onclose: function () {
        document.getElementById('searchCtInput').value = dropdownCreditNumber.getValue();
    },
    width: '590px'
});