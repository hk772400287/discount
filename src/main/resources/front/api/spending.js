const deleteSpending = (id) => {
    return $axios({
        url: `/userSpending/${id}`,
        method: 'delete'
    })
}


function addSpending (params) {
    return $axios({
        url: '/userSpending',
        method: 'post',
        data: { ...params }
    })
}