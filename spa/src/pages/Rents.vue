<template>
    <q-page>
        <q-table
        title="Rents"
        :data="data"
        :columns="columns"
        row-key="name"
        ></q-table>
    </q-page>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { Rent } from 'src/model/rent';

@Component({
  components: {},
})
export default class Rents extends Vue {
    columns = [
      {
        name: 'id',
        required: true,
        label: 'Record Id',
        align: 'left',
        field: (row:Rent) => row.id,
        format: (val:string) => val,
        sortable: true,
      },
      {
        name: 'rentDate',
        required: true,
        label: 'Rent Date',
        align: 'left',
        field: (row: Rent) => row.rentDate,
        format: (val) => `${val}`,
        sortable: true,
      },
      {
        name: 'returnDate',
        label: 'Return Date',
        align: 'left',
        field: (row) => row.returnDate,
        format: (val) => `${val}`,
        sortable: true,
      }];

    data: Rent[] = [];

    async loadData(): Promise<Rent[]> {
      const response = await this.$axios.get<Rent[]>('https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/events');
      return response.data;
    }

    created() {
      this.loadData()
        .then((data) => {
          this.data = data;
        }).catch((err) => console.log(err));
    }
}
</script>
