<template>
  <q-layout>
    <q-page-container>
        <router-view/>
    </q-page-container>
  </q-layout>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';

export default class WelcomeLayout extends Vue {

}
</script>
