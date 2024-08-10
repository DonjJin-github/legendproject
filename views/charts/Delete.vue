<template>
  <div>
    <AppSidebar />
    <div class="wrapper d-flex flex-column min-vh-100">
      <AppHeader />
      <div class="body flex-grow-1">
        <CContainer class="px-4" lg>
          <CRow style="margin-top: 2vh;" class="justify-content-center align-items-center h-100">
            <CCol md="15">
              <CCard class="custom-card">
                <CCardHeader>
                  <div style="display: flex; justify-content: space-between; align-items: center;">
                    <h4 class="card-title mb-0">데이터 삭제</h4>
                    <button type="button" class="btn btn-secondary update-btn" @click="deleteSelectedItems">
                      <img src="@/assets/image/delete.png" alt="삭제" class="delete-icon"/>
                    </button>
                  </div>
                </CCardHeader>
                <CCardBody>
                  <table>
                    <thead>
                      <tr>
                        <th></th>
                        <th>No.</th>
                        <th>Date</th>
                        <th>GPS</th>
                        <th>Information</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(item, index) in hazardData" :key="index">
                        <td class="checkbox-cell">
                          <input 
                            type="checkbox" 
                            v-model="item.selected"
                          />
                        </td>
                        <td>{{ index + 1 }}</td>
                        <td>{{ item.dates }}</td>
                        <td>{{ item.gps }}</td>
                        <td>{{ item.hazardType }}</td>
                      </tr>
                    </tbody>
                  </table>
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
        </CContainer>
      </div>
      <AppFooter />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { CContainer, CRow, CCol, CCard, CCardHeader, CCardBody } from '@coreui/vue'
import AppFooter from '@/components/AppFooter.vue'
import AppHeader from '@/components/AppHeader.vue'
import AppSidebar from '@/components/AppSidebar.vue'

const hazardData = ref([])

async function fetchHazardData() {
  try {
    const response = await axios.get('http://localhost/api/hazarddata')
    console.log("API Response Data:", response.data); // 응답 데이터 확인용 로그

    // 데이터 매핑
    hazardData.value = response.data.map((item, index) => ({
      ...item,
      selected: false,
      no: item.no !== undefined ? item.no : index + 1 // 'no' 속성이 없으면 index를 사용
    }))

    console.log("Fetched Data:", hazardData.value); // 데이터 확인용 로그
  } catch (error) {
    console.error("데이터를 가져오는 중 오류가 발생했습니다:", error)
  }
}

onMounted(() => {
  fetchHazardData()
})

async function deleteSelectedItems() {
  // 항목의 hid 값이 올바르게 설정되어 있는지 확인하는 로그 추가
  hazardData.value.forEach(item => {
    console.log(`Item HID: ${item.hid}, Selected: ${item.selected}`);
  });

  const itemsToDelete = hazardData.value
    .filter(item => item.selected && item.hid !== undefined) // undefined인 경우를 필터링
    .map(item => item.hid) // 'hid' 값을 사용하여 삭제할 항목을 선택

  if (itemsToDelete.length === 0) {
    console.error("삭제할 항목이 없습니다. 또는 항목의 HID가 잘못되었습니다.");
    return;
  }

  console.log("Items to delete:", itemsToDelete); // 삭제할 항목 확인용 로그

  for (const itemHid of itemsToDelete) {
    try {
      await axios.delete(`http://localhost/api/hazarddata/delete/${itemHid}`)
      hazardData.value = hazardData.value.filter(item => item.hid !== itemHid)
    } catch (error) {
      console.error(`HID ${itemHid} 항목을 삭제하는 중 오류가 발생했습니다:`, error)
    }
  }
  window.location.reload();
}
</script>

<style lang="scss" scoped>
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th{
  color: #ffffff;
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #212631;
}

.checkbox-cell {
  text-align: center;
}
.update-btn {
  background-color: #a6a6a6;
  border: none;
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  padding: 0;
  border-radius: 4px;
}
.delete-icon {
  width: 24px;
  height: 24px;
}
</style>
