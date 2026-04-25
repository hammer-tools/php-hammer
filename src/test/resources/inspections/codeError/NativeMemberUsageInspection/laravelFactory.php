<?php

namespace Illuminate\Database\Eloquent\Factories {
    abstract class Factory
    {
        public function state($state): static
        {
            return $this;
        }
    }
}

namespace Illuminate\Database\Eloquent {
    /**
     * @property int $id
     * @property string $name
     */
    class Model {}
}

namespace App\Models {
    class Company extends \Illuminate\Database\Eloquent\Model {}
}

namespace App\Factories {
    class CompanyFactory extends \Illuminate\Database\Eloquent\Factories\Factory
    {
        public function forCompany(\App\Models\Company $company): static
        {
            return $this->state([
                'company_id' => $company->id,
            ]);
        }
    }
}

